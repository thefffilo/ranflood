package org.sssfile.files;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.sssfile.util.Security;


public class OriginalFile {

	public final Path path;
	public final LinkedHashMap<Integer, byte[]> parts;
	private LinkedList<Path> shards_paths = null;
	private byte[] hash_original_file;

	public final int	n,
						k;
	public final long	generation;

	private Iterator<Map.Entry<Integer, byte[]>> iterator;

	

	public OriginalFile(
			Path path, byte[] hash_original_file, Map<Integer, byte[]> parts,
			int n, int k, long generation
	) {
		this.path	= path;
		this.parts	= new LinkedHashMap<>(parts);
		this.hash_original_file = hash_original_file;
		this.n = n;
		this.k = k;
		this.generation = generation;
	}

	public OriginalFile(
			Path path, byte[] hash_original_file,
			int n, int k, long generation
	) {
		this.path	= path;
		this.parts	= new LinkedHashMap<>();
		this.hash_original_file = hash_original_file;
		this.n = n;
		this.k = k;
		this.generation = generation;
	}


	public void addPart(Integer part_number, byte[] part) {
		parts.put(part_number, part);
	}

	public void addShardPath(Path shard_path) {
		if(shards_paths == null) {
			shards_paths = new LinkedList<Path>();
		}
		shards_paths.add(shard_path);
	}

	public LinkedList<Path> getShardsPaths() {
		return shards_paths;
	}


	/**
	 * Used to iteratively return the content of a shard to write.
	 * If the hash is not given, it's calculated.
	 * @return the content of the next shard, or null if got all
	 * @throws NoSuchAlgorithmException on computing hashes
	 */
	public byte[] iterateShardContent() throws NoSuchAlgorithmException {

		if(iterator == null)
			iterator = parts.entrySet().iterator();

		if(!iterator.hasNext()) {
			iterator = null;
			return null;
		}
		Map.Entry<Integer, byte[]> entry = iterator.next();

		ShardFile shard = new ShardFile(
			path, path, hash_original_file,
			n, k, generation,
			entry.getKey(), entry.getValue()
		);

		return shard.getContent();
	}


	/*
	 * Get/Set
	 */

	public String getHashBase64() {
		return Base64.getEncoder().encodeToString( hash_original_file );
	}
	public boolean isValid(byte[] content) throws NoSuchAlgorithmException {
		return Arrays.equals(hash_original_file, Security.hashBytes(content));
	}


	/**
	 * Read this path's content, then calculate and save the hash.
	 * @throws IOException -
	 * @throws NoSuchAlgorithmException -
	 */
	public static byte[] readHash(Path path) throws IOException, NoSuchAlgorithmException {
		return Security.hashFileContent(path);
	}


	/*
	 * Object
	 */

	@Override
	public boolean equals(Object obj) {
        if (this == obj)
            return true;

		if (obj == null || getClass() != obj.getClass())
            return false;

        OriginalFile that = (OriginalFile)obj;
        return Arrays.equals(hash_original_file, that.hash_original_file);
    }

	
}
